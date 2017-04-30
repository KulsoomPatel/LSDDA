package lsdda

import org.bson.types.ObjectId

class Programme {

    ObjectId id
    String pid
    Double start_time
    Double end_time
    String media_type
    String service
    Double is_clip
    ArrayList<String> tags
    CompleteTitle complete_title
    Categories categories
    Double score

    static embedded = ['complete_title', 'categories']
    static constraints = {
    }

    static mapWith = "mongo"

    static mapping = {

        collection "programmeData"

    }
}
