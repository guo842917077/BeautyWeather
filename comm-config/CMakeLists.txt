cmake_minimum_required(VERSION 3.6.0-rc2)

set(PROJECT_NAME CommConfig)
add_library(${PROJECT_NAME} SHARED src/main/cpp/config.cpp)
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -L$-L${CMAKE_SOURCE_DIR}/src/main/jniLibs/${ANDROID_ABI}")
find_library(log-lib log)

target_link_libraries(${PROJECT_NAME}
        ${log-lib}
        z
        android)