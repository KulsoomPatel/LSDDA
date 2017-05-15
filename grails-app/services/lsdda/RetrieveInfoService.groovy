package lsdda

import com.mongodb.BasicDBList
import com.mongodb.BasicDBObject
import com.mongodb.MongoClient
import com.mongodb.client.FindIterable
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoCursor
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Sorts
import grails.transaction.Transactional
import org.bson.Document


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

    def getTheClipType() {

        MongoCursor<String> c =
                collection.distinct("is_clip", String.class).iterator()

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

        BasicDBObject criteria = new BasicDBObject();

        criteria.put('$text', new BasicDBObject('$search', value))

        def scoreProj = ['$meta': "textScore"]
        def theProj = ["score": scoreProj]

        FindIterable iterable = collection.find(criteria).projection(theProj).sort(theProj)

        return iterable
    }

    def advancedQuery(String value, int is_clip, String media_type, String service, Double start_time, Double end_time, String[] tags, String[] cats) {

        BasicDBObject criteria = new BasicDBObject();
        def sorting = ["start_time": -1]

        if (value != null) {
            criteria.put('$text', new BasicDBObject('$search', value))

        }

        if (start_time != null) {
            //greater than or equal to
            criteria.put("start_time", new BasicDBObject('$gte', start_time))
        }

        if (end_time != null) {
            //less than or equal to
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
            sorting.put("is_clip", 1)
        }

        if (cats.length != 0) {

            ArrayList<BasicDBObject> orList = new ArrayList<>()
            ArrayList<BasicDBObject> andList = new ArrayList<>()

            def catClass = new Categories()
            //minus 1 as the ID is included.
            def theCats = catClass.properties.size() - 1

            for (int i = 1; i <= theCats; i++) {
                String identifier = "categories.category" + i

                orList.add(new BasicDBObject(identifier, new BasicDBObject('$in', cats)))
            }
            andList.add(new BasicDBObject('$or', orList))
            criteria.put('$and', andList)

        }

        //in the array
        if (tags.length != 0) {
            criteria.put("tags", new BasicDBObject('$all', tags))
        }

        FindIterable iterable
        if (value == null) {
            //and by default
            iterable = collection.find(criteria).sort(sorting)
        } else {
            def scoreProj = ['$meta': "textScore"]
            def theProj = ["score": scoreProj]
            iterable = collection.find(criteria).projection(theProj).sort(theProj)
        }


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
