package lsdda

import com.mongodb.BasicDBList
import com.mongodb.BasicDBObject
import com.mongodb.BasicDBObjectBuilder
import com.mongodb.DBCursor
import com.mongodb.MongoClient
import com.mongodb.client.FindIterable
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoCursor
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Projections
import com.mongodb.client.model.Sorts
import grails.transaction.Transactional
import org.bson.BSON
import org.bson.Document
import org.bson.conversions.Bson
import org.xml.sax.DocumentHandler


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
        MongoCursor<String> c =
                collection.distinct("categories.category1", String.class).iterator()
        MongoCursor<String> d =
                collection.distinct("categories.category2", String.class).iterator()
        MongoCursor<String> e =
                collection.distinct("categories.category3", String.class).iterator()
        MongoCursor<String> f =
                collection.distinct("categories.category4", String.class).iterator()
        MongoCursor<String> g =
                collection.distinct("categories.category5", String.class).iterator()

        def allcats = [c.toList(), d.toList(), e.toList(), f.toList(), g.toList()]

        allcats.each { list ->
            list.each { it ->
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

    def advancedQuery(Double is_clip, String media_type, String service) {

        List<BasicDBObject> fields = new ArrayList<BasicDBObject>();
        Document document = new Document()

        if (is_clip != null) {
            BasicDBObject isClip = new BasicDBObject("is_clip", is_clip)
            fields.add(isClip)
        }

        if (media_type != null) {
            BasicDBObject mediaType = new BasicDBObject("media_type", media_type)
            fields.add(mediaType)
        }

        if (service != null) {
            BasicDBObject channel = new BasicDBObject("service", service)
            fields.add(channel)
        }

        document.put("and", fields)

        FindIterable<Programme> iterable = collection.find(document)

        return iterable
    }
}
