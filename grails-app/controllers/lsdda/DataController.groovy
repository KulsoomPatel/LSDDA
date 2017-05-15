package lsdda

import grails.rest.*
import grails.converters.*

class DataController {

    def retrieveInfoService

    def getBBCServices() {

        def theServices = retrieveInfoService.getTheService()

        respond theServices
    }

    def getBBCMediaType() {

        def theMediaTypes = retrieveInfoService.getTheMediaType()

        respond theMediaTypes
    }

    def getBBCTags() {

        def theTags = retrieveInfoService.getTheTags()

        respond theTags
    }

    def getTheBBCCategories() {

        def theCategories = retrieveInfoService.getTheCategories()

        respond theCategories
    }

    def getTheClip() {

        def theClip = retrieveInfoService.getTheClipType()

        respond theClip
    }
}
