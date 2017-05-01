package lsdda

import grails.transaction.Transactional

@Transactional
class SearchDBService {

    def makeSearch(String value) {

        def count = Programme.countHits(value)
        List<Programme> programmes = Programme.searchTop(value, count)

        return programmes

    }
}
