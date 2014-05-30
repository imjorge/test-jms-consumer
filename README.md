[![Build Status](https://snap-ci.com/imjorge/test-jms-consumer/branch/master/build_image)](https://snap-ci.com/imjorge/test-jms-consumer/branch/master)

![Build Status](https://www.codeship.io/projects/16d92850-ca3d-0131-e4eb-121d56ccd2c2/status)

test-jms-consumer
=================

Reads JMS messages from a configurable destination.

First, build the project:
> mvn clean install

Create a conf directory:
> mkdir conf

Copy examples to conf directory:
> cp examples\* conf\

Edit necessary settings inside files on `conf` folder.

Start to listen for JMS messages. Stop with `Ctrl+C`.

> java -jar target\test-jms-consumer-0.0.1-SNAPSHOT.one-jar.jar -c conf\application.jboss4.properties

Received JMS messages will be printed to standard output. First it outputs JMS headers in the format `key=value` and, afterwards, a `{` (not part of the JMS message), the payload of the text message and it ends with a `}` (not part of the JMS message).

The output (for a received JMS message) is suitable as input for [test-jms-producer](https://github.com/imjorge/test-jms-consumer).
