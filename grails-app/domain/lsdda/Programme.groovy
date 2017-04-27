package lsdda

import org.bson.types.ObjectId

class Programme {

    ObjectId id
    String pid
    Long start_time
    Long end_time
    String media_type
    String service
    Long is_clip
    ArrayList<String> tags
    CompleteTitle complete_title
    Categories categories

    static embedded = ['complete_title', 'categories']
    static constraints = {
    }

    static mapWith = "mongo"

    static mapping = {

        collection "programmeData"

    }
}
