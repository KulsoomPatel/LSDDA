package lsdda

import grails.transaction.Transactional

@Transactional
class SearchDBService {

    def makeSearch(String value) {

        def programmes = Programme.findByService(value)

        return programmes

    }
}
