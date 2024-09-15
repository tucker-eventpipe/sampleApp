package com.mycompany.sample


import groovy.transform.CompileStatic
import io.micronaut.data.mongodb.annotation.MongoRepository
import io.micronaut.data.repository.CrudRepository

@CompileStatic
@MongoRepository
interface TestContentRepository extends CrudRepository<TestContent, String> {

    Optional<TestContent> findByPropertyId(String propertyId)
}