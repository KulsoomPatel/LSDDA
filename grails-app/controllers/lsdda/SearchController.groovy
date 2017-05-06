package lsdda

import grails.rest.*
import grails.converters.*

class SearchController {

    def retrieveInfoService

    def searchData(String value) {

        def results = retrieveInfoService.textSearch(value)

        respond results, model: [programmeCount: results.size()]
    }

    def advancedSearch(String value, int is_clip, String media_type, String service) {

        def results = retrieveInfoService.advancedQuery(value, is_clip, media_type, service)

        respond results, model: [programmeCount: results.size()]
    }

}
