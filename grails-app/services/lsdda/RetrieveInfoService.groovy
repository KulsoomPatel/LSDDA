package lsdda

import com.mongodb.BasicDBObject
import com.mongodb.MongoClient
import com.mongodb.client.FindIterable
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoCursor
import com.mongodb.client.MongoDatabase
import grails.transaction.Transactional


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

        def theCats = Categories.fields.size()

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

        def count = Programme.countHits(value)
        List<Programme> programmes = Programme.searchTop(value, count)

        return programmes
    }

    def advancedQuery(String value, int is_clip, String media_type, String service, Double start_time, Double end_time, String[] tags, String[] cats) {

        BasicDBObject criteria = new BasicDBObject();

        if (value != null) {
            criteria.put('$text', new BasicDBObject('$search', value))
        }
        if (is_clip != null) {
            criteria.put("is_clip", is_clip)
        }

        if (media_type != null) {
            criteria.put("media_type", media_type)
        }

        if (service != null) {
            criteria.put("service", service)

        }

        if (start_time != null) {
            //greater than or equal to
            criteria.put("start_time", new BasicDBObject('$gte', start_time))
        }

        if (end_time != null) {
            //less than or equal to
            criteria.put("end_time", new BasicDBObject('$lte', end_time))
        }

        //in the array
        if (tags != null) {
            criteria.put("tags", new BasicDBObject('$in', tags))
        }

        if (cats != null) {
            //counts the number of categories available
            def theCats = Categories.fields.size()

            for (int i = 1; i <= theCats; i++) {
                String identifier = "categories.category" + i

                
            }
        }

        //and by default
        FindIterable iterable = collection.find(criteria)

        return iterable
    }
}
