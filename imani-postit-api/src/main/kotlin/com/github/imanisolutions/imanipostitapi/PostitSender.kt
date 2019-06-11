package com.github.imanisolutions.imanipostitapi

import nyla.solutions.core.security.user.data.UserProfile
import nyla.solutions.core.util.Config
import nyla.solutions.core.util.JavaBean
import nyla.solutions.core.util.Text
import nyla.solutions.email.Email
import java.time.LocalDate

class PostitSender
{
    val template = Config.getProperty("EMAIL_TEMPLATE")
    val subject =  Config.getProperty("EMAIL_SUBJECT")
    var dao = PostitDao();


    /**
     * Send the anniversary notification
     */
    fun sendAnniversaryCard(userProfile : UserProfile) : String
    {

        var aBindMap = JavaBean.toMap(userProfile)


        var emailBody = Text.getTextStyles().format(template,aBindMap);


        var email = Email()
        email.sendMail(userProfile.email,subject,emailBody)

        return emailBody

    }//----------------------------------------------------------

    /**
     * Send anniversaries for today
     */
    fun sendTodayAnniversaries(): Collection<UserProfile> {

        var users = dao.selectAnniversariesByDate(LocalDate.now());

        for ( user in users)
        {
            this.sendAnniversaryCard(user)
        }

        return users
    }//----------------------------------------------------------
}