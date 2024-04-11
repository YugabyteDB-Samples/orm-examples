cd src/application/ && \
go build -o ../../bin/application && \
go install && \
cd ../.. && \
echo "Build completed successfully. Launching application..." && \
./bin/application