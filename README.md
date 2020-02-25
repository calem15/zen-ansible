# zen-ansible
## How to use playbook
* Run base.ym for initial server setup `ansible-playbook -i inventory/${server_env} base.yml`
* Run deploy-db.yml for DB initial set-up `ansible-playbook -i inventory/${server_env} deploy-db.yml`
### Note on deploy-db.yml, it contains env for Postgres DB credential, don't forget to update it.
