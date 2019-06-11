package com.github.imanisolutions.imanipostitapi

import io.pivotal.services.dataTx.geode.client.GeodeClient
import java.time.LocalDateTime
import org.junit.Assert.*
import nyla.solutions.core.security.user.data.UserProfile
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Ignore
import org.junit.Test
import java.time.LocalDate

@Ignore
class PostitDaoTest
{
    @Before
    fun  setup()
    {
        var geode = GeodeClient.connect()
        var users = geode.getRegion<String,UserProfile>("users")
        var imani = UserProfile();
        imani.phone = "555-323-2323"
        imani.email = "im@email.io"
        imani.firstName = "Imani"
        imani.lastName = "Green"
        users.put("delete1",imani)


        var dao = PostitDao()

        dao.saveAnniversary(LocalDate.now(),imani);

    }

        @Test
        fun testHasRecords()
        {
            var dao = PostitDao()
            var date = LocalDate.now();


            var list : Collection<UserProfile> = dao.selectAnniversariesByDate(date)
            assertTrue(!list.isEmpty())

        }

}