package lsdda

import grails.transaction.Transactional

@Transactional
class SearchDBService {

    def makeSearch(String value) {

        List<Programme> programmes = Programme.search(value)

        return programmes

    }
}
