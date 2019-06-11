package com.github.imanisolutions.imanipostitapi

import io.pivotal.services.dataTx.geode.client.GeodeClient
import nyla.solutions.core.security.user.data.UserProfile
import org.apache.geode.cache.Region
import java.lang.IllegalArgumentException
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.collections.HashSet

/**
 * @author Gregory Green
 *
 * This is a Data Access Object for sending content
 */
class PostitDao
{
    /**
     * Select users with anniversaries
     */
    fun selectAnniversariesByDate(useDate: LocalDate): Collection<UserProfile> {

        var geode = GeodeClient.connect();

        var anniversaries = anniversariesRegion(geode)

        var usersRegion = userRegion(geode)


        var userKeys = anniversaries!!.get(useDate)

        if(userKeys == null) {
            return Collections.EMPTY_LIST as Collection<UserProfile>
        }

        var userProfiles = usersRegion!!.getAll(userKeys);

        if(userProfiles == null || userProfiles.isEmpty())
            return Collections.EMPTY_LIST as Collection<UserProfile>;


        return userProfiles.values.filterNotNull();
    }//--------------------------------------------


    fun saveAnniversary(anniversaryDate: LocalDate, user: UserProfile)
    {
        //check if user is saves

        var id = user.id;

        if(id == null || id.length ==0)
            user.id = user.email

       if(user.id == null || user.id.length == 0)
           throw IllegalArgumentException("User.id or User.email required");

        var geode = GeodeClient.connect();
        var anniversariesRegion = anniversariesRegion(geode)
        var usersRegion = userRegion(geode)


        //check if users is
        var userInRegion = usersRegion!!.get(user.id)

        ///Save user if not there
        if(userInRegion == null)
        {
            usersRegion.put(user.id,user)
        }

        var userKeys : MutableSet<String>? = anniversariesRegion!!.get(anniversaryDate);

        if(userKeys == null || userKeys.isEmpty())
        {
            userKeys = HashSet<String>()
        }

        userKeys.add(user.id.toString())

        anniversariesRegion.put(anniversaryDate,userKeys)

    }//--------------------------------------------

    private fun anniversariesRegion(geode: GeodeClient): Region<LocalDate, MutableSet<String>>? {
        var anniversaries = geode.getRegion<LocalDate, MutableSet<String>>("anniversaries")
        return anniversaries
    }

    private fun userRegion(geode: GeodeClient): Region<String, UserProfile>? {
        var users = geode.getRegion<String, UserProfile>("users")
        return users
    }

}
