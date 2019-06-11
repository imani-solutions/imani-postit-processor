package com.github.imanisolutions.imanipostitapi

import nyla.solutions.core.security.user.data.UserProfile
import nyla.solutions.core.util.Config
import org.junit.Test
import org.junit.Assert.*
import org.junit.Ignore
import java.time.LocalDate

@Ignore
class PostitSenderTest
{

    @Test
    fun testSending()
    {

        var postitSender = PostitSender()
        var userProfile = buildUser()

        var content :String = postitSender.sendAnniversaryCard(userProfile)

        assertTrue("content:"+content+"  is not blank",content.length > 0)

        assertTrue("content:"+content+"  contains Happy Anniversary",
                        content.contains("Happy Anniversary"))

        assertTrue("content:"+content+"  contains Imani",
                content.contains("Imani"))
    }



    @Test
    fun testSendTodayAnniversaries()
    {
        //Save a user
        var gg = buildUser()

        var postitSender = PostitSender()

        var dao= PostitDao()
        dao.saveAnniversary(LocalDate.now(),gg)

        var userProfiles : Collection<UserProfile> =postitSender.sendTodayAnniversaries();

        assertTrue("User profiles not empty", userProfiles.size > 0 )

        userProfiles.contains(gg)

    }//--------------------------------
    private fun buildUser(): UserProfile {
        var userProfile = UserProfile()
        userProfile.firstName = "Imani"
        userProfile.email = Config.getProperty("TO_EMAIL_TEST")
        return userProfile
    }
}