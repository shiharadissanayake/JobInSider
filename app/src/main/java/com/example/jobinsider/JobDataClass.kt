package com.example.jobinsider

class JobDataClass {
    var jobtitle: String? = null
    var jobdesc: String? = null
    var jobqualification: String? = null
    var companyname: String? = null

    constructor(jobtitle: String?, jobdesc: String?, jobqualification: String?, companyname: String?){
        this.jobtitle = jobtitle
        this.jobdesc = jobdesc
        this.jobqualification = jobqualification
        this.companyname = companyname
    }
    constructor()
    {}
}