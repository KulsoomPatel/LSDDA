package lsdda

import grails.transaction.Transactional

@Transactional
class SearchDBService {

    def makeSearch(String value) {

        List<Programme> programmes = Programme.findAllByService(value)

        return programmes

    }
}
