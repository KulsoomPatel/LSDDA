package lsdda

import com.mongodb.MongoClient
import com.mongodb.client.MongoCursor
import com.mongodb.client.MongoDatabase
import grails.transaction.Transactional

@Transactional
class RetrieveInfoService {

    MongoClient mongo = new MongoClient()
    MongoDatabase db = mongo.getDatabase("bbcData")

    def getTheService() {

        MongoCursor<String> c =
                db.getCollection("programmeData").distinct("service", String.class).iterator()

        return c.toList().sort()
    }

    def getTheMediaType() {

        MongoCursor<String> c =
                db.getCollection("programmeData").distinct("media_type", String.class).iterator()

        return c.toList().sort()
    }

    def getTheTags() {
        MongoCursor<String> c =
                db.getCollection("programmeData").distinct("tags", String.class).iterator()

        return c.toList().sort()
    }

    def getTheCategories() {

        LinkedHashSet<String> categories = new LinkedHashSet<String>()
        MongoCursor<String> c =
                db.getCollection("programmeData").distinct("categories.category1", String.class).iterator()
        MongoCursor<String> d =
                db.getCollection("programmeData").distinct("categories.category2", String.class).iterator()
        MongoCursor<String> e =
                db.getCollection("programmeData").distinct("categories.category3", String.class).iterator()
        MongoCursor<String> f =
                db.getCollection("programmeData").distinct("categories.category4", String.class).iterator()
        MongoCursor<String> g =
                db.getCollection("programmeData").distinct("categories.category5", String.class).iterator()

        def allcats = [c.toList(), d.toList(), e.toList(), f.toList(), g.toList()]

        allcats.each { list ->
            list.each { it ->
                categories.add(it)
            }
        }

        return categories.sort()
    }


}
