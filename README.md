# ClamAV REST Client

## ClamAv

ClamAV is an open source (GPLv2) anti-virus toolkit, designed especially for e-mail scanning on mail gateways. It provides a number of utilities including a flexible and scalable multi-threaded daemon, a command line scanner and advanced tool for automatic database updates. The core of the package is an anti-virus engine available in a form of shared library.

More information and the official documentation: https://docs.clamav.net/

### Starting the ClamAv daemon

In the root of the project, you'll find a docker-compose definition to start ClamAv.

Just execute the following command to start the daemon in the same folder as the compose file:

```shell
$ docker compose up --remove-orphans
```

Wait until you see the message

```
clamav-1  | socket found, clamd started.
```

> Note, once you connect to the daemon and you shut down the web application, the container will stop the process. You'll need to stop the container and restart it again.


## ClamAV REST API

Requires Java 17 for compilation and run-time.

### Configuration

There is a `application.properties` file located in `src/main/resources/`, you can change the connection parameters to the ClamAV instance there.

### Starting the REST API

You can just start the `Application` class, or run the mvn command `mvn spring-boot:run`

#### Utility mappings

- /util/ping: checks if the application is responsive
- /util/version: returns the current version of ClamAv
- /util/stats: the current states of the scan activities 

#### Admin mappings
- /admin/reload: reload the definition database
- /admin/shutdown: shutdown the ClamAv instance

#### Scan mapping
- /scan: takes a multipart formdata upload (`file`) and will return a response.

Possible responses:

- No viruses found:
```json
{
    "results": {},
    "status": "OK"
}
```
- Error while scanning of viruses: 
```json
{
    "results": {},
    "status": "ERROR"
}
```

- Virus(es) found: 
```json
{
    "results": {
      "stream" : ["name of the virus"]
    },
    "status": "VIRUS_FOUND"
}
```


