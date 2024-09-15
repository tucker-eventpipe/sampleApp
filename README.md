## Test app for mongo issue
This app reproduces a could the issues serializing to mongo, see the stack traces below.

### Setup locally
1. For mongo db run  
```bash
docker-compose -f ./mongo.yml up -d 
```
2. Open up the TestStartupService, where the test code is
3. Run the app with the run task under application
4. Note the exception
5. 5 shutdown the mongo instance

```bash
docker-compose -f ./mongo.yml down
```


## Serializing a map in Micronaut 4.5+
This works under 4.4.3 which I have a branch for called 443, but it doesn't work under 4.6 and gets the following error:
```
15:32:04.598 [main] ERROR io.micronaut.runtime.Micronaut - Error starting Micronaut server: Error executing PERSIST: Cannot serialize: com.myCompanycontent.TestContent@30bd39d5
io.micronaut.data.exceptions.DataAccessException: Error executing PERSIST: Cannot serialize: com.myCompanycontent.TestContent@30bd39d5
	at io.micronaut.data.runtime.operations.internal.BaseOperations.failed(BaseOperations.java:141)
	at io.micronaut.data.runtime.operations.internal.BaseOperations.persist(BaseOperations.java:90)
	at io.micronaut.data.runtime.operations.internal.EntityOperations.persist(EntityOperations.java:31)
	at io.micronaut.data.mongodb.operations.DefaultMongoRepositoryOperations.persistOne(DefaultMongoRepositoryOperations.java:701)
	at io.micronaut.data.mongodb.operations.DefaultMongoRepositoryOperations.lambda$persist$11(DefaultMongoRepositoryOperations.java:515)
	at io.micronaut.data.mongodb.operations.DefaultMongoRepositoryOperations.lambda$withClientSession$19(DefaultMongoRepositoryOperations.java:758)
	at io.micronaut.data.connection.support.AbstractConnectionOperations.executeWithNewConnection(AbstractConnectionOperations.java:143)
	at io.micronaut.data.connection.support.AbstractConnectionOperations.execute(AbstractConnectionOperations.java:90)
	at io.micronaut.data.mongodb.operations.DefaultMongoRepositoryOperations.withClientSession(DefaultMongoRepositoryOperations.java:758)
	at io.micronaut.data.mongodb.operations.DefaultMongoRepositoryOperations.persist(DefaultMongoRepositoryOperations.java:513)
	at io.micronaut.data.runtime.intercept.DefaultSaveEntityInterceptor.intercept(DefaultSaveEntityInterceptor.java:45)
	at io.micronaut.data.runtime.intercept.DataIntroductionAdvice.intercept(DataIntroductionAdvice.java:83)
	at io.micronaut.aop.chain.MethodInterceptorChain.proceed(MethodInterceptorChain.java:143)
	at com.myCompanycontent.TestContentRepository$Intercepted.save(Unknown Source)
	at org.codehaus.groovy.vmplugin.v8.IndyInterface.fromCache(IndyInterface.java:321)
	at com.myCompany.TestStartupService.onStartup(TestStartupService.groovy:34)
	at com.myCompany.$TestStartupService$Definition$Exec.dispatch(Unknown Source)
	at io.micronaut.context.AbstractExecutableMethodsDefinition$DispatchedExecutableMethod.invoke(AbstractExecutableMethodsDefinition.java:456)
	at io.micronaut.context.DefaultBeanContext$BeanExecutionHandle.invoke(DefaultBeanContext.java:3940)
	at io.micronaut.aop.chain.AdapterIntroduction.intercept(AdapterIntroduction.java:87)
	at io.micronaut.aop.chain.MethodInterceptorChain.proceed(MethodInterceptorChain.java:143)
	at com.myCompany.TestStartupService$ApplicationEventListener$onStartup1$Intercepted.onApplicationEvent(Unknown Source)
	at io.micronaut.context.event.ApplicationEventPublisherFactory.notifyEventListeners(ApplicationEventPublisherFactory.java:266)
	at io.micronaut.context.event.ApplicationEventPublisherFactory$2.publishEvent(ApplicationEventPublisherFactory.java:226)
	at io.micronaut.context.DefaultBeanContext.publishEvent(DefaultBeanContext.java:1831)
	at io.micronaut.context.DefaultBeanContext.start(DefaultBeanContext.java:360)
	at io.micronaut.context.DefaultApplicationContext.start(DefaultApplicationContext.java:215)
	at io.micronaut.runtime.Micronaut.start(Micronaut.java:75)
	at io.micronaut.runtime.Micronaut.run(Micronaut.java:334)
	at io.micronaut.runtime.Micronaut.run(Micronaut.java:320)
	at com.myCompany.Application.main(Application.groovy:10)
Caused by: io.micronaut.data.exceptions.DataAccessException: Cannot serialize: com.myCompanycontent.TestContent@30bd39d5
	at io.micronaut.data.mongodb.serde.MappedCodec.encode(MappedCodec.java:97)
	at org.bson.codecs.BsonDocumentWrapperCodec.encode(BsonDocumentWrapperCodec.java:63)
Caused by: io.micronaut.data.exceptions.DataAccessException: Cannot serialize: com.myCompanycontent.TestContent@30bd39d5

	at org.bson.codecs.BsonDocumentWrapperCodec.encode(BsonDocumentWrapperCodec.java:29)
	at com.mongodb.internal.connection.SplittablePayload$WriteRequestEncoder.encode(SplittablePayload.java:195)
	at com.mongodb.internal.connection.SplittablePayload$WriteRequestEncoder.encode(SplittablePayload.java:182)
	at org.bson.codecs.BsonDocumentWrapperCodec.encode(BsonDocumentWrapperCodec.java:63)
	at org.bson.codecs.BsonDocumentWrapperCodec.encode(BsonDocumentWrapperCodec.java:29)
	at com.mongodb.internal.connection.BsonWriterHelper.writeDocument(BsonWriterHelper.java:77)
	at com.mongodb.internal.connection.BsonWriterHelper.writePayload(BsonWriterHelper.java:59)
	at com.mongodb.internal.connection.CommandMessage.encodeMessageBodyWithMetadata(CommandMessage.java:162)
	at com.mongodb.internal.connection.RequestMessage.encode(RequestMessage.java:136)
	at com.mongodb.internal.connection.CommandMessage.encode(CommandMessage.java:59)
	at com.mongodb.internal.connection.InternalStreamConnection.sendAndReceive(InternalStreamConnection.java:360)
	at com.mongodb.internal.connection.UsageTrackingInternalConnection.sendAndReceive(UsageTrackingInternalConnection.java:114)
	at com.mongodb.internal.connection.DefaultConnectionPool$PooledConnection.sendAndReceive(DefaultConnectionPool.java:765)
	at com.mongodb.internal.connection.CommandProtocolImpl.execute(CommandProtocolImpl.java:76)
	at com.mongodb.internal.connection.DefaultServer$DefaultServerProtocolExecutor.execute(DefaultServer.java:209)
	at com.mongodb.internal.connection.DefaultServerConnection.executeProtocol(DefaultServerConnection.java:115)
	at com.mongodb.internal.connection.DefaultServerConnection.command(DefaultServerConnection.java:83)
	at com.mongodb.internal.connection.DefaultServer$OperationCountTrackingConnection.command(DefaultServer.java:307)
	at com.mongodb.internal.operation.MixedBulkWriteOperation.executeCommand(MixedBulkWriteOperation.java:395)
	at com.mongodb.internal.operation.MixedBulkWriteOperation.executeBulkWriteBatch(MixedBulkWriteOperation.java:259)
	at com.mongodb.internal.operation.MixedBulkWriteOperation.lambda$execute$2(MixedBulkWriteOperation.java:203)
	at com.mongodb.internal.operation.SyncOperationHelper.lambda$withSourceAndConnection$0(SyncOperationHelper.java:127)
	at com.mongodb.internal.operation.SyncOperationHelper.withSuppliedResource(SyncOperationHelper.java:152)
	at com.mongodb.internal.operation.SyncOperationHelper.lambda$withSourceAndConnection$1(SyncOperationHelper.java:126)
	at com.mongodb.internal.operation.SyncOperationHelper.withSuppliedResource(SyncOperationHelper.java:152)
	at com.mongodb.internal.operation.SyncOperationHelper.withSourceAndConnection(SyncOperationHelper.java:125)
	at com.mongodb.internal.operation.MixedBulkWriteOperation.lambda$execute$3(MixedBulkWriteOperation.java:188)
	at com.mongodb.internal.operation.MixedBulkWriteOperation.lambda$decorateWriteWithRetries$0(MixedBulkWriteOperation.java:146)
	at com.mongodb.internal.async.function.RetryingSyncSupplier.get(RetryingSyncSupplier.java:67)
	at com.mongodb.internal.operation.MixedBulkWriteOperation.execute(MixedBulkWriteOperation.java:207)
	at com.mongodb.internal.operation.MixedBulkWriteOperation.execute(MixedBulkWriteOperation.java:77)
	at com.mongodb.client.internal.MongoClientDelegate$DelegateOperationExecutor.execute(MongoClientDelegate.java:173)
	at com.mongodb.client.internal.MongoCollectionImpl.executeSingleWriteRequest(MongoCollectionImpl.java:1085)
	at com.mongodb.client.internal.MongoCollectionImpl.executeInsertOne(MongoCollectionImpl.java:478)
	at com.mongodb.client.internal.MongoCollectionImpl.insertOne(MongoCollectionImpl.java:473)
	at io.micronaut.data.mongodb.operations.DefaultMongoRepositoryOperations$2.execute(DefaultMongoRepositoryOperations.java:771)
	at io.micronaut.data.runtime.operations.internal.BaseOperations.persist(BaseOperations.java:84)
	. 29 common frames omitted
Caused by: io.micronaut.serde.exceptions.SerdeException: Expected an instance of BsonWriterEncoder got: io.micronaut.serde.support.util.JsonNodeEncoder$Outer@2d230cff
	at io.micronaut.serde.bson.custom.AbstractBsonSerde.asBson(AbstractBsonSerde.java:63)
Caused by: io.micronaut.serde.exceptions.SerdeException: Expected an instance of BsonWriterEncoder got: io.micronaut.serde.support.util.JsonNodeEncoder$Outer@2d230cff

	at io.micronaut.serde.bson.custom.AbstractBsonSerde.serialize(AbstractBsonSerde.java:46)
	at io.micronaut.serde.support.serializers.RuntimeTypeSerializer.serialize(RuntimeTypeSerializer.java:78)
	at io.micronaut.serde.support.serializers.CustomizedMapSerializer.encodeMapKey(CustomizedMapSerializer.java:158)
	at io.micronaut.serde.support.serializers.CustomizedMapSerializer$1.serializeInto(CustomizedMapSerializer.java:74)
	at io.micronaut.serde.support.serializers.CustomizedMapSerializer$1.serialize(CustomizedMapSerializer.java:61)
	at io.micronaut.serde.support.serializers.CustomizedMapSerializer$1.serialize(CustomizedMapSerializer.java:56)
	at io.micronaut.serde.support.serializers.CustomizedObjectSerializer.serializeInto(CustomizedObjectSerializer.java:136)
	at io.micronaut.serde.support.serializers.CustomizedObjectSerializer.serialize(CustomizedObjectSerializer.java:56)
	at io.micronaut.serde.support.serializers.ErrorCatchingSerializer.serialize(ErrorCatchingSerializer.java:45)
	at io.micronaut.data.mongodb.serde.MappedCodec.encode(MappedCodec.java:95)
	. 67 common frames omitted
```


## Serializing a BigDecimal with too many digits
For this one comment out the first `TestContent testContent` and comment in the second one and run.
```
15:30:58.939 [main] ERROR io.micronaut.runtime.Micronaut - Error starting Micronaut server: Error executing PERSIST: Cannot serialize: com.myCompanycontent.TestContent@2cc61b3b
io.micronaut.data.exceptions.DataAccessException: Error executing PERSIST: Cannot serialize: com.myCompanycontent.TestContent@2cc61b3b
	at io.micronaut.data.runtime.operations.internal.BaseOperations.failed(BaseOperations.java:141)
	at io.micronaut.data.runtime.operations.internal.BaseOperations.persist(BaseOperations.java:90)
	at io.micronaut.data.runtime.operations.internal.EntityOperations.persist(EntityOperations.java:31)
	at io.micronaut.data.mongodb.operations.DefaultMongoRepositoryOperations.persistOne(DefaultMongoRepositoryOperations.java:701)
	at io.micronaut.data.mongodb.operations.DefaultMongoRepositoryOperations.lambda$persist$11(DefaultMongoRepositoryOperations.java:515)
	at io.micronaut.data.mongodb.operations.DefaultMongoRepositoryOperations.lambda$withClientSession$19(DefaultMongoRepositoryOperations.java:758)
	at io.micronaut.data.connection.support.AbstractConnectionOperations.executeWithNewConnection(AbstractConnectionOperations.java:143)
	at io.micronaut.data.connection.support.AbstractConnectionOperations.execute(AbstractConnectionOperations.java:90)
	at io.micronaut.data.mongodb.operations.DefaultMongoRepositoryOperations.withClientSession(DefaultMongoRepositoryOperations.java:758)
	at io.micronaut.data.mongodb.operations.DefaultMongoRepositoryOperations.persist(DefaultMongoRepositoryOperations.java:513)
	at io.micronaut.data.runtime.intercept.DefaultSaveEntityInterceptor.intercept(DefaultSaveEntityInterceptor.java:45)
	at io.micronaut.data.runtime.intercept.DataIntroductionAdvice.intercept(DataIntroductionAdvice.java:83)
	at io.micronaut.aop.chain.MethodInterceptorChain.proceed(MethodInterceptorChain.java:143)
	at com.myCompanycontent.TestContentRepository$Intercepted.save(Unknown Source)
	at org.codehaus.groovy.vmplugin.v8.IndyInterface.fromCache(IndyInterface.java:321)
	at com.myCompany.TestStartupService.onStartup(TestStartupService.groovy:34)
	at com.myCompany.$TestStartupService$Definition$Exec.dispatch(Unknown Source)
	at io.micronaut.context.AbstractExecutableMethodsDefinition$DispatchedExecutableMethod.invoke(AbstractExecutableMethodsDefinition.java:456)
	at io.micronaut.context.DefaultBeanContext$BeanExecutionHandle.invoke(DefaultBeanContext.java:3940)
	at io.micronaut.aop.chain.AdapterIntroduction.intercept(AdapterIntroduction.java:87)
	at io.micronaut.aop.chain.MethodInterceptorChain.proceed(MethodInterceptorChain.java:143)
	at com.myCompany.TestStartupService$ApplicationEventListener$onStartup1$Intercepted.onApplicationEvent(Unknown Source)
	at io.micronaut.context.event.ApplicationEventPublisherFactory.notifyEventListeners(ApplicationEventPublisherFactory.java:266)
	at io.micronaut.context.event.ApplicationEventPublisherFactory$2.publishEvent(ApplicationEventPublisherFactory.java:226)
	at io.micronaut.context.DefaultBeanContext.publishEvent(DefaultBeanContext.java:1831)
	at io.micronaut.context.DefaultBeanContext.start(DefaultBeanContext.java:360)
	at io.micronaut.context.DefaultApplicationContext.start(DefaultApplicationContext.java:215)
	at io.micronaut.runtime.Micronaut.start(Micronaut.java:75)
	at io.micronaut.runtime.Micronaut.run(Micronaut.java:334)
	at io.micronaut.runtime.Micronaut.run(Micronaut.java:320)
	at com.myCompany.Application.main(Application.groovy:10)
Caused by: io.micronaut.data.exceptions.DataAccessException: Cannot serialize: com.myCompanycontent.TestContent@2cc61b3b
	at io.micronaut.data.mongodb.serde.MappedCodec.encode(MappedCodec.java:97)
Caused by: io.micronaut.data.exceptions.DataAccessException: Cannot serialize: com.myCompanycontent.TestContent@2cc61b3b

	at org.bson.codecs.BsonDocumentWrapperCodec.encode(BsonDocumentWrapperCodec.java:63)
	at org.bson.codecs.BsonDocumentWrapperCodec.encode(BsonDocumentWrapperCodec.java:29)
	at com.mongodb.internal.connection.SplittablePayload$WriteRequestEncoder.encode(SplittablePayload.java:195)
	at com.mongodb.internal.connection.SplittablePayload$WriteRequestEncoder.encode(SplittablePayload.java:182)
	at org.bson.codecs.BsonDocumentWrapperCodec.encode(BsonDocumentWrapperCodec.java:63)
	at org.bson.codecs.BsonDocumentWrapperCodec.encode(BsonDocumentWrapperCodec.java:29)
	at com.mongodb.internal.connection.BsonWriterHelper.writeDocument(BsonWriterHelper.java:77)
	at com.mongodb.internal.connection.BsonWriterHelper.writePayload(BsonWriterHelper.java:59)
	at com.mongodb.internal.connection.CommandMessage.encodeMessageBodyWithMetadata(CommandMessage.java:162)
	at com.mongodb.internal.connection.RequestMessage.encode(RequestMessage.java:136)
	at com.mongodb.internal.connection.CommandMessage.encode(CommandMessage.java:59)
	at com.mongodb.internal.connection.InternalStreamConnection.sendAndReceive(InternalStreamConnection.java:360)
	at com.mongodb.internal.connection.UsageTrackingInternalConnection.sendAndReceive(UsageTrackingInternalConnection.java:114)
	at com.mongodb.internal.connection.DefaultConnectionPool$PooledConnection.sendAndReceive(DefaultConnectionPool.java:765)
	at com.mongodb.internal.connection.CommandProtocolImpl.execute(CommandProtocolImpl.java:76)
	at com.mongodb.internal.connection.DefaultServer$DefaultServerProtocolExecutor.execute(DefaultServer.java:209)
	at com.mongodb.internal.connection.DefaultServerConnection.executeProtocol(DefaultServerConnection.java:115)
	at com.mongodb.internal.connection.DefaultServerConnection.command(DefaultServerConnection.java:83)
	at com.mongodb.internal.connection.DefaultServer$OperationCountTrackingConnection.command(DefaultServer.java:307)
	at com.mongodb.internal.operation.MixedBulkWriteOperation.executeCommand(MixedBulkWriteOperation.java:395)
	at com.mongodb.internal.operation.MixedBulkWriteOperation.executeBulkWriteBatch(MixedBulkWriteOperation.java:259)
	at com.mongodb.internal.operation.MixedBulkWriteOperation.lambda$execute$2(MixedBulkWriteOperation.java:203)
	at com.mongodb.internal.operation.SyncOperationHelper.lambda$withSourceAndConnection$0(SyncOperationHelper.java:127)
	at com.mongodb.internal.operation.SyncOperationHelper.withSuppliedResource(SyncOperationHelper.java:152)
	at com.mongodb.internal.operation.SyncOperationHelper.lambda$withSourceAndConnection$1(SyncOperationHelper.java:126)
	at com.mongodb.internal.operation.SyncOperationHelper.withSuppliedResource(SyncOperationHelper.java:152)
	at com.mongodb.internal.operation.SyncOperationHelper.withSourceAndConnection(SyncOperationHelper.java:125)
	at com.mongodb.internal.operation.MixedBulkWriteOperation.lambda$execute$3(MixedBulkWriteOperation.java:188)
	at com.mongodb.internal.operation.MixedBulkWriteOperation.lambda$decorateWriteWithRetries$0(MixedBulkWriteOperation.java:146)
	at com.mongodb.internal.async.function.RetryingSyncSupplier.get(RetryingSyncSupplier.java:67)
	at com.mongodb.internal.operation.MixedBulkWriteOperation.execute(MixedBulkWriteOperation.java:207)
	at com.mongodb.internal.operation.MixedBulkWriteOperation.execute(MixedBulkWriteOperation.java:77)
	at com.mongodb.client.internal.MongoClientDelegate$DelegateOperationExecutor.execute(MongoClientDelegate.java:173)
	at com.mongodb.client.internal.MongoCollectionImpl.executeSingleWriteRequest(MongoCollectionImpl.java:1085)
	at com.mongodb.client.internal.MongoCollectionImpl.executeInsertOne(MongoCollectionImpl.java:478)
	at com.mongodb.client.internal.MongoCollectionImpl.insertOne(MongoCollectionImpl.java:473)
	at io.micronaut.data.mongodb.operations.DefaultMongoRepositoryOperations$2.execute(DefaultMongoRepositoryOperations.java:771)
	at io.micronaut.data.runtime.operations.internal.BaseOperations.persist(BaseOperations.java:84)
	. 29 common frames omitted
Caused by: io.micronaut.serde.exceptions.SerdeException: Error serializing value at path: 
	at io.micronaut.serde.support.serializers.ErrorCatchingSerializer.serialize(ErrorCatchingSerializer.java:53)
	at io.micronaut.data.mongodb.serde.MappedCodec.encode(MappedCodec.java:95)
Caused by: io.micronaut.serde.exceptions.SerdeException: Error serializing value at path: 

	. 67 common frames omitted
Caused by: java.lang.NumberFormatException: Conversion to Decimal128 would require inexact rounding of 9.6438461538461538461538461538461538461538
	at org.bson.types.Decimal128.ensureExactRounding(Decimal128.java:250)
	at org.bson.types.Decimal128.clampAndRound(Decimal128.java:239)
	at org.bson.types.Decimal128.<init>(Decimal128.java:175)
	at org.bson.types.Decimal128.<init>(Decimal128.java:162)
Caused by: java.lang.NumberFormatException: Conversion to Decimal128 would require inexact rounding of 9.6438461538461538461538461538461538461538

	at org.bson.codecs.BigDecimalCodec.encode(BigDecimalCodec.java:34)
	at org.bson.codecs.BigDecimalCodec.encode(BigDecimalCodec.java:30)
	at io.micronaut.serde.bson.custom.CodecBsonDecoder.doSerialize(CodecBsonDecoder.java:55)
	at io.micronaut.serde.bson.custom.AbstractBsonSerde.serialize(AbstractBsonSerde.java:46)
	at io.micronaut.serde.support.serializers.CustomizedObjectSerializer.serializeInto(CustomizedObjectSerializer.java:136)
	at io.micronaut.serde.support.serializers.CustomizedObjectSerializer.serialize(CustomizedObjectSerializer.java:56)
	at io.micronaut.serde.support.serializers.ErrorCatchingSerializer.serialize(ErrorCatchingSerializer.java:45)
	. 68 common frames omitted

```