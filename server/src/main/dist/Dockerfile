#
# @APP_NAME@ dockerfile.
#

# Pull base image.
FROM @BASE_IMAGE@

COPY ./@APP_NAME@/ /@APP_NAME@/

# Define working directory.
WORKDIR /work-dir

ENTRYPOINT ["/@APP_NAME@/start.sh"]

EXPOSE @PORT@