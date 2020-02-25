FROM phpdockerio/php71-fpm:latest
COPY . /qa-test
WORKDIR "/qa-test"
ARG DEBIAN_FRONTEND=noninteractive
RUN apt-get update \
    && apt-get -y --no-install-recommends install  php7.1-pgsql php7.1-gd php-redis php7.1-json \
    && apt-get clean; rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/* /usr/share/doc/*
RUN apt-get update \
    && apt-get -y install git \
    && apt-get clean; rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/* /usr/share/doc/*
RUN curl -s https://getcomposer.org/installer | php
RUN mv composer.phar /usr/bin/composer
RUN php composer install --no-dev
RUN php artisan preset none
EXPOSE 8000
ENTRYPOINT bash -c "php artisan serve"
