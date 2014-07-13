#!/bin/sh

wget https://github.com/twbs/bootstrap/archive/v3.2.0.zip
unzip v3.2.0.zip 
mv bootstrap-3.2.0/fonts public/fonts
mv bootstrap-3.2.0/less public/less
rm -r bootstrap-3.2.0
rm v3.2.0.zip
