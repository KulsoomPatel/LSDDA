package lsdda

import grails.rest.*
import grails.converters.*

class SearchController {

    def searchDBService

    def searchData(String value) {

        def results = searchDBService.makeSearch(value)
    }
}
