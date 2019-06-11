package com.github.imanisolutions.imanipostitapi

import nyla.solutions.core.io.IO
import nyla.solutions.core.security.user.data.UserProfile
import org.junit.Assert.*
import org.junit.Ignore
import org.junit.Test

@Ignore
class PostitServiceTest
{

    @Test
    fun testSaveByCsv()
    {
        var postitService = PostitService()

        var csv = IO.readFile("src/test/resources/test.csv")
        var users : Collection<UserProfile> = postitService.saveAnniversariesByCsv(csv)

        assertTrue(users.size > 0)
    }//--------------------------------------------------------------

    @Test
    fun testSendToday()
    {
        var service = PostitService()


        service.sendToday()

    }//--------------------------------------------------------------
}