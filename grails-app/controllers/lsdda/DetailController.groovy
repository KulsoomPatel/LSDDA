package lsdda


import grails.rest.*
import grails.converters.*

class DetailController {

    def retrieveInfoService

    def detailedProgramme(String pid) {

        def programme = retrieveInfoService.getProgrammeData(pid)

        respond programme, model:[programme: programme]
    }
}
