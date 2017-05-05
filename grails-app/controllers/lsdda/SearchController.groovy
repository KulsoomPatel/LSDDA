package lsdda

import grails.rest.*
import grails.converters.*

class SearchController {

    def retrieveInfoService

    def searchData(String value) {

        def results = retrieveInfoService.textSearch(value)

        respond results, model: [programmeCount: results.size()]
    }

}
