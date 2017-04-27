package lsdda

class CompleteTitle {

    String name
    String series
    String episode

    static constraints = {
    }

    static mapWith = "mongo"

    static mapping = {

        collection "programmeData"

    }
}
