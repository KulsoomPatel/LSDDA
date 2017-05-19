package lsdda

import com.mongodb.BasicDBList
import com.mongodb.BasicDBObject
import com.mongodb.DBObject
import com.mongodb.MongoClient
import com.mongodb.client.AggregateIterable
import com.mongodb.client.FindIterable
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoCursor
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Sorts
import grails.transaction.Transactional
import org.bson.Document

import javax.persistence.Basic


@Transactional
class RetrieveInfoService {

    MongoClient mongo = new MongoClient()
    MongoDatabase db = mongo.getDatabase("bbcData")
    MongoCollection collection = db.getCollection("programmeData")

    def getTheService() {

        MongoCursor<String> c =
                collection.distinct("service", String.class).iterator()

        return c.toList().sort()
    }

    def getTheMediaType() {

        MongoCursor<String> c =
                collection.distinct("media_type", String.class).iterator()

        return c.toList().sort()
    }

    def getTheTags() {
        MongoCursor<String> c =
                collection.distinct("tags", String.class).iterator()

        return c.toList().sort()
    }

    def getTheCategories() {

        LinkedHashSet<String> categories = new LinkedHashSet<String>()

        def catClass = new Categories()
        //minus 1 as the ID is included.
        def theCats = catClass.properties.size() - 1

        for (int i = 1; i <= theCats; i++) {

            String identifier = "categories.category" + i
            MongoCursor<String> c =
                    collection.distinct(identifier, String.class).iterator()

            c.each { it ->
                categories.add(it)
            }
        }

        return categories.sort()
    }

    def textSearch(String value) {

        FindIterable iterable

        if (value != null) {
            BasicDBObject criteria = new BasicDBObject();

            criteria.put('$text', new BasicDBObject('$search', value))

            def scoreProj = ['$meta': "textScore"]
            def theProj = ["score": scoreProj]

            iterable = collection.find(criteria).projection(theProj).sort(theProj)

            return iterable
        } else {

            def sorting = [:]
            sorting.put("start_time", -1)
            sorting.put("complete_title.name", 1)
            sorting.put("service", 1)
            sorting.put("media_type", 1)

            iterable = collection.find().sort(sorting)

            return iterable
        }


    }

    def advancedQuery(String value, Integer is_clip, String media_type, String service, Double start_time, Double end_time, String[] tags, String[] cats) {


        List<BasicDBObject> pipeline = new ArrayList<>()
        BasicDBObject criteria = new BasicDBObject()
        BasicDBObject theProjections = new BasicDBObject()
        BasicDBObject sorting = new BasicDBObject()
        AggregateIterable iterable

        theProjections.put("_id", 1)
        theProjections.put("pid", 1)
        theProjections.put("start_time", 1)
        theProjections.put("end_time", 1)
        theProjections.put("complete_title", 1)
        theProjections.put("media_type", 1)
        theProjections.put("service", 1)
        theProjections.put("is_clip", 1)
        theProjections.put("categories", 1)
        theProjections.put("tags", 1)

        if (value != null) {

            criteria.put('$text', new BasicDBObject('$search', value))
            sorting.put('score', new BasicDBObject('$meta', "textScore"))
        }

        sorting.put("start_time", -1)
        if (start_time != null) {

            criteria.put("start_time", new BasicDBObject('$gte', start_time))

        }

        if (end_time != null) {
            criteria.put("end_time", new BasicDBObject('$lte', end_time))
        }

        if (service != null) {
            criteria.put("service", service)
            sorting.put("service", 1)
        }

        if (media_type != null) {
            criteria.put("media_type", media_type)
            sorting.put("media_type", 1)
        }

        if (is_clip != null) {
            criteria.put("is_clip", is_clip)
        }

        if (cats.length != 0) {

            ArrayList<BasicDBObject> orList = new ArrayList<>()
            ArrayList<BasicDBObject> andList = new ArrayList<>()
            BasicDBList theMegaArray = new BasicDBList()

            def catClass = new Categories()

            //minus 1 as the ID is included.
            def theCats = catClass.properties.size() - 1

            for (int i = 1; i <= theCats; i++) {

                String identifier = "categories.category" + i
                String cleanIdentifier = '$' + identifier
                //If the category does not exist, put in a blank category
                theMegaArray.add(new BasicDBObject('$ifNull', Arrays.asList(cleanIdentifier, Collections.EMPTY_LIST)))

                orList.add(new BasicDBObject(identifier, new BasicDBObject('$all', cats)))
            }

            theProjections.put("allCategories", new BasicDBObject('$setUnion', theMegaArray))
            orList.add(new BasicDBObject("allCategories", new BasicDBObject('$all', cats)))

            andList.add(new BasicDBObject('$or', orList))
            criteria.put('$and', andList)
        }

        //in the array

        if (tags.length != 0) {
            criteria.put("tags", new BasicDBObject('$all', tags))
        }

        pipeline.add(new BasicDBObject('$project', theProjections))
        pipeline.add(new BasicDBObject('$match', criteria))
        pipeline.add(new BasicDBObject('$sort', sorting))

        //and by default
        iterable = collection.aggregate(pipeline)


        return iterable
    }

    def getProgrammeData(String pid) {

        BasicDBObject criteria = new BasicDBObject("pid", pid)
        Document programme = collection.findOne(criteria)

        return programme

    }

    def getRelatedProgrammes(String title) {

        BasicDBObject criteria = new BasicDBObject();

        criteria.put("complete_title.name", title)

        def sorting = ["start_time": -1]

        FindIterable iterable = collection.find(criteria).sort(sorting)

        return iterable

    }

}
