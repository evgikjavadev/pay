  set -e

  echo "Start liquibase process"
  connects=($(echo $CONNECTIONS_DB | tr "%" "\n"))
  connections_count=${#connects[*]}

  echo "Processing [${connections_count}] connections..."
  for ((i = 0; i < connections_count; i++)); do
      echo "Connection: ${connects[i]}"
      connect=${!connects[i]}
      prm=($(echo $connect | tr ";" "\n"))
      url=${prm[0]}
      username=${!prm[1]}
      password=${!prm[2]}
      schema=$DEFAULT_SCHEMA
      liqui_schema=$LIQUIBASE_SCHEMA

      echo "Processing [$url]... on [$liqui_schema / $schema] as [$username]"
      if liquibase --liquibase-schema-name=$liqui_schema --url=$url --username=$username --password=$password tag-exists $VERSION 2>&1 | grep -F 'already exists'; then
          if [[ "$LIQUIBASE_ROLLBACK" == "true" ]]; then
              echo "Rolling back to [$VERSION]..."
              liquibase --liquibase-schema-name=$liqui_schema --schema-name=$schema --changeLogFile=changelog/liquibase-changeLog.xml --url=$url --username=$username --password=$password rollback $VERSION
          fi
      else
          # liquibase --liquibase-schema-name=$liqui_schema --url=$url --username=$username --password=$password clear-checksums
          liquibase --liquibase-schema-name=$liqui_schema --schema-name=$schema --changeLogFile=changelog/liquibase-changeLog.xml --url=$url --username=$username --password=$password update
          liquibase --liquibase-schema-name=$liqui_schema --schema-name=$schema --url=$url --username=$username --password=$password tag $VERSION
      fi
  done