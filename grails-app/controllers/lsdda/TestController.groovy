package lsdda


import grails.rest.*
import grails.converters.*

class TestController {

    def index() {

        Programme f = Programme.findByService("bbc radio four")


        render(f.pid + " " + f.complete_title.name + " " + f.complete_title.series + " " + f.complete_title.episode + " " + f.service + " " + f.is_clip + " " + f.media_type + " " + f.tags.toString())
    }
}
