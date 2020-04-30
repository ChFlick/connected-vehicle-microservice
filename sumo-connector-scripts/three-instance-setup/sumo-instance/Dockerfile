FROM ubuntu:bionic

ENV MOST_VERSION 0.6
ENV SCRIPT_FOLDER /app
ENV SUMO_VERSION 1.6.0
ENV SUMO_HOME /usr/share/sumo

RUN apt-get update && apt-get install -y \
    cmake \
    python3-pip \
    g++ \
    libxerces-c-dev libfox-1.6-dev libgdal-dev libproj-dev libgl2ps-dev \
    swig \
    wget
RUN update-alternatives --install /usr/bin/python python /usr/bin/python3 1
RUN wget http://downloads.sourceforge.net/project/sumo/sumo/version%20$SUMO_VERSION/sumo-src-$SUMO_VERSION.tar.gz
RUN tar xzf sumo-src-$SUMO_VERSION.tar.gz && \
    mv sumo-$SUMO_VERSION $SUMO_HOME && \
    rm sumo-src-$SUMO_VERSION.tar.gz

RUN cd $SUMO_HOME  && \
    mkdir build/cmake-build && cd build/cmake-build  && \
    cmake ../..  && \
    make -j$(nproc)

ENV PYTHONPATH "$PYTHONPATH:/$SUMO_HOME/tools"
ENV PATH "$PATH:/$SUMO_HOME/bin"

WORKDIR $SCRIPT_FOLDER

RUN wget https://github.com/lcodeca/MoSTScenario/archive/v$MOST_VERSION.tar.gz && \
    tar -xzf v$MOST_VERSION.tar.gz && \
    rm v$MOST_VERSION.tar.gz

COPY . .
RUN chmod +x start.sh

CMD $SCRIPT_FOLDER/start.sh
