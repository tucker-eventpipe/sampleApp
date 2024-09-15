package com.mycompany


import com.mycompany.sample.TestContent
import com.mycompany.sample.TestContentRepository
import io.micronaut.context.event.StartupEvent
import io.micronaut.runtime.event.annotation.EventListener
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class TestStartupService {
    @Inject
    TestContentRepository testContentRepository


    @EventListener
    void onStartup(StartupEvent event) {
        TestContent testContent = new TestContent(
                id: 'test',
                category: [
                        one:'one',
                        two:2,
                        three: false
                ]
        )

//        TestContent testContent = new TestContent(
//                number: 9.6438461538461538461538461538461538461538
//        )

        testContentRepository.save(testContent)
    }
}
