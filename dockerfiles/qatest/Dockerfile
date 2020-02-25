FROM calem15/qa-test:base
COPY . /qa-test
WORKDIR "/qa-test"
RUN rm -rf node_modules
EXPOSE 8000
ENTRYPOINT bash -c "php artisan serve"
