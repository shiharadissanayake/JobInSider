package com.example.jobinsider


class DataClass {

        var fulllname: String? = null
        var jobposition: String? = null
        var time: String? = null
        var town: String? = null

        constructor(fulllname: String?, jobposition: String?, time: String?, town: String?){
            this.fulllname = fulllname
            this.jobposition = jobposition
            this.time = time
            this.town = town
        }
        constructor()
        {}
    }
