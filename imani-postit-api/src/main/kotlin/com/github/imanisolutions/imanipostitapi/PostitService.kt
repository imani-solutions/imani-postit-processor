package com.github.imanisolutions.imanipostitapi

import nyla.solutions.core.io.csv.CsvReader
import nyla.solutions.core.security.user.data.UserProfile
import nyla.solutions.core.util.Text
import java.io.StringReader
import java.lang.IllegalArgumentException
import java.util.*
import kotlin.collections.ArrayList

class PostitService
{
    val dao = PostitDao();
    val sender = PostitSender()

    fun saveAnniversariesByCsv(csv: String?): Collection<UserProfile>
    {

        if(csv == null || csv.length == 0)
            return Collections.EMPTY_LIST as Collection<UserProfile>


        var csvText = CsvReader(StringReader(csv));

        var len = csvText.size()

        var users = ArrayList<UserProfile>(len);

        for(i in 0 until len ){
            var row = csvText.row(i)

            if(row.size < 2)
                throw IllegalArgumentException("Line "+i+1+" expected minimum 2 columns date,email")

            var user = UserProfile()

            var f = 0;

            var dateText = row.get(f++);

            var anniversaryDate = Text.toLocalDate(dateText,"M/dd/yyyy")

            user.id = row.get(f++)

            if(f < row.size)
                user.email = row.get(f++)
            if(f < row.size)
                user.firstName = row.get(f++)
            if(f < row.size)
                user.lastName = row.get(f++)
            if(f < row.size)
                user.phone = row.get(f)

            dao.saveAnniversary(anniversaryDate,user)

            users.add(user)
        }

        return users

    }//--------------------------------------------

    /**
     * Send today's anniversaries
     */
    fun sendToday() {
       sender.sendTodayAnniversaries()
    }

}