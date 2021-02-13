ITERATIONS ?= 5
ARGUMENT ?= 3
JAVA_OPTS ?= "-Xmx170m"

.PHONY: build-native build-python run-native run-python

build-native:
	@docker build -t python-polyglot:1.0.0 -f graalvm/Dockerfile .

build-python:
	@docker build -t python-python:1.0.0 -f python/Dockerfile .

run-python: build-python
	@docker run -e ITERATIONS=$(ITERATIONS) -e ARGUMENT=$(ARGUMENT) --memory 200m python-python:1.0.0

run-native: build-native
	@docker run -e JAVA_OPTS=$(JAVA_OPTS) -e ITERATIONS=$(ITERATIONS) -e ARGUMENT=$(ARGUMENT) --memory 256m python-polyglot:1.0.0