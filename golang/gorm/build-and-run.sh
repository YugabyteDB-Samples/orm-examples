cd src/application/ && \
go build && \
go install && \
cd ../.. && \
echo "Build completed successfully. Launching application..." && \
./bin/application
