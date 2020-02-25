FROM calem15/qa-test:base
COPY . /qa-test
WORKDIR "/qa-test"
RUN composer install --no-dev
RUN /usr/bin/php artisan preset none
EXPOSE 8000
ENTRYPOINT bash -c "php artisan serve"
