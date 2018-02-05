#!/usr/bin/env bash

RESOURCE_DIR=$(cd $(dirname $0)/resources && pwd)
echo $RESOURCE_DIR

# chap03
wget http://www.cl.ecei.tohoku.ac.jp/nlp100/data/jawiki-country.json.gz -P ${RESOURCE_DIR} 
gzip -d ${RESOURCE_DIR}/jawiki-country.json.gz

#chap04
wget http://www.cl.ecei.tohoku.ac.jp/nlp100/data/neko.txt -P ${RESOURCE_DIR} 
