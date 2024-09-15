package com.mycompany.sample


import groovy.transform.CompileStatic
import io.micronaut.core.annotation.Introspected
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.serde.annotation.Serdeable
import jakarta.persistence.Id

@CompileStatic
@Serdeable
@MappedEntity
@Introspected
class TestContent {


    @Id
    String id

    BigDecimal number


    String propertyId = null    // Property name.




    Map category = null
}
