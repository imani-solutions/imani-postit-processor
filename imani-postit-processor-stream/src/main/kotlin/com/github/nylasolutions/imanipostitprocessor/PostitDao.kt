package com.github.nylasolutions.imanipostitprocessor

import java.time.LocalDateTime
import nyla.solutions.core.security.user.data.UserProfile

class PostitDao {

    /**
     * Select the users will matching anniversary dates
     */
    fun selectAnniversaryByDate(date : LocalDateTime) : List<UserProfile>
    {
        var users = arrayListOf<UserProfile>(UserProfile())

        return users;
    }
}
