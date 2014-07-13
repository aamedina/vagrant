# vagrant

A Leiningen template for a Clojure and/or ClojureScript project with a Vagrant dev environment. Puppet is used for provisioning the virtual machine.

# Usage

`lein new vagrant application-name`

./Vagrantfile contains the default Vagrant configuration. This is pretty vanilla, but connects it to the local .puppet directory which contains the default puppet script. It also assumes you have your modules installed in your host's home .puppet directory.

If you don't use VMware Fusion with Vagrant, you may remove the VMWare specific block in the Vagrantfile with the configuration options of your choosing with VirtualBox or your provider of choice.

./.puppet/manifests/default.pp contains the default puppet script

To download the Bootstrap LESS source code, go into the resources folder and execute the shell script "setup.sh". This will also add the glyphicon font files to the public directory.

To provision the virtual machine for the first time, simply run `vagrant up` as you normally would to bootstrap the VM. After the VM is provisioned, `vagrant ssh` into it and run `lein`. Leiningen needs to be executed manually to bootstrap itself. 

After Leiningen is working, open /etc/nginx/conf.d/default.conf as root and change the 'location /' route's root to /vagrant/resources/public - this will tell nginx to use your project's public directory as the root to serve resources from.

You may also uncomment the error_page 404 directive to use the default 404 page for missing resources.

I personally like to launch a headless project repl on the Vagrant machine and connect to it with CIDER from host machine. Simply cd to /vagrant (which is the default shared folder, so it mirrors the project directory on the host machine) and execute `lein repl :headless`. It will read in the repl-options in the project.clj, and launch a headless REPL at port 4041.

If you use CIDER, you may simply cider-connect to 127.0.0.1:4041 on your host Emacs client to connect to the guest headless REPL.

Voila! That should be it. Have fun developing!

## License

Copyright © 2014 Adrian Medina

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
