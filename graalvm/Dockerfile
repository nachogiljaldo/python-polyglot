FROM maven:3.6.3-jdk-11 AS build-jar
WORKDIR /build
COPY pom.xml /build
RUN mvn dependency:go-offline
COPY src/main/java /build/src/main/java
RUN mvn clean install

FROM ghcr.io/graalvm/graalvm-ce:21.0.0 AS build
RUN gu install native-image
RUN gu install python
WORKDIR /build
COPY --from=build-jar /build/target/python-polyglot-1.0-SNAPSHOT.jar /build/app.jar
RUN pwd
RUN cd /build
RUN native-image --initialize-at-build-time= -H:InitialCollectionPolicy=com.oracle.svm.core.genscavenge.CollectionPolicy\$BySpaceAndTime -H:+JNI -H:FallbackThreshold=0 -H:+ReportExceptionStackTraces -H:-AddAllCharsets -H:EnableURLProtocols=http -H:NativeLinkerOption=-no-pie --no-server -H:-UseServiceLoaderFeature -H:+StackTrace --language:python -jar app.jar -H:Name=output
WORKDIR /opt
RUN mkdir -p /opt && cd /opt
RUN cp /build/output /opt/ && \
    rm -rf /build
COPY src/main/python/benchmark/fannkuch.py /opt/fannkuch.py
WORKDIR /opt
RUN chmod 777 /opt/output
CMD ["sh", "-c", "/opt/output $JAVA_OPTS"]