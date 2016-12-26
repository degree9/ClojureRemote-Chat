FROM degree9/heroku-boot-clj:latest

ENV BOOT_VERSION=2.7.1

ENV BOOT_CLOJURE_VERSION 1.8.0

RUN boot heroku
