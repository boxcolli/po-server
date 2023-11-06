#include <iostream>
#include <curl/curl.h>
#include <string>

// Callback function for handling data received from the server
static size_t WriteCallback(void *contents, size_t size, size_t nmemb, std::string *data) {
    data->append((char*)contents, size * nmemb);
    return size * nmemb;
}

std::string makeHttpRequest(const std::string& url) {
    CURL *curl;
    CURLcode res;
    std::string readBuffer;

    curl = curl_easy_init();
    if(curl) {
        curl_easy_setopt(curl, CURLOPT_URL, url.c_str());
        curl_easy_setopt(curl, CURLOPT_WRITEFUNCTION, WriteCallback);
        curl_easy_setopt(curl, CURLOPT_WRITEDATA, &readBuffer);

        // Perform the request, res will get the return code
        res = curl_easy_perform(curl);

        // Check for errors
        if(res != CURLE_OK) {
            std::cerr << "curl_easy_perform() failed: " << curl_easy_strerror(res) << std::endl;
            readBuffer = "Error";
        }

        // Always cleanup
        curl_easy_cleanup(curl);
    }
    return readBuffer;
}

int main() {
    std::string url = "http://localhost:8080/ping";
    std::string response = makeHttpRequest(url);

    std::cout << "Response: " << response << std::endl;
    
    return 0;
}
