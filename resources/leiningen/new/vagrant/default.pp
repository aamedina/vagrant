include apt

apt::source { 'puppetlabs':
  location   => 'http://apt.puppetlabs.com',
  repos      => 'main',
  key        => '4BD6EC30',
  key_server => 'pgp.mit.edu',
}

exec { "apt-update":
  command => "apt-get update",
  path => "/usr/bin:/usr/sbin:/bin:/usr/local/bin:/usr/local/sbin:/sbin"
}

exec { "apt-upgrade":
  command => "apt-get --quiet --yes --fix-broken upgrade",
  path => "/usr/bin:/usr/sbin:/bin:/usr/local/bin:/usr/local/sbin:/sbin",
  require => Exec['apt-update']
}

package { ['build-essential', 
           'bash-completion', 
           'libffi-dev',
           'libreadline-dev',
           'unzip']:
  require => Exec['apt-upgrade'],  
  ensure => 'installed',  
}

include java
include nginx

exec { 'install-leiningen':
  require => Class['java'],
	command => 'wget https://raw.github.com/technomancy/leiningen/stable/bin/lein -O /usr/local/bin/lein',
  path => "/usr/bin:/usr/sbin:/bin:/usr/local/bin:/usr/local/sbin:/sbin",
  creates => '/usr/local/bin/lein',
}

file { '/usr/local/bin/lein':
  mode => 755,
  require => Exec["install-leiningen"],
}

exec { 'init-lein':
  command => '/usr/local/bin/lein',
  creates => '/home/vagrant/.lein',
  require => Exec["install-leiningen"]
}

file { '/home/vagrant/.lein':
  ensure => "directory",
  require => Exec["init-lein"]
}

exec { 'init-profiles':
  command => 'echo "{:user {:plugins [[cider/cider-nrepl \"0.7.0-SNAPSHOT\"]]}}" > /home/vagrant/.lein/profiles.clj',
  path => "/usr/bin:/usr/sbin:/bin:/usr/local/bin:/usr/local/sbin:/sbin",
  creates => '/home/vagrant/.lein/profiles.clj',
  require => File['/home/vagrant/.lein']
}

file { '/home/vagrant/.lein/profiles.clj':
  require => Exec["init-profiles"]
}

exec { 'install-grench':
	command => 'wget https://grenchman.s3.amazonaws.com/downloads/grench-0.2.0-ubuntu -O /usr/local/bin/grench',
  path => "/usr/bin:/usr/sbin:/bin:/usr/local/bin:/usr/local/sbin:/sbin",
  creates => '/usr/local/bin/grench',
  require => File['/home/vagrant/.lein/profiles.clj'],
}

file { '/usr/local/bin/grench': 
  mode => 755,
  require => Exec['install-grench']
}

exec { 'init-repl-port':
  command => 'echo "4040" > /home/vagrant/.lein/repl-port',
  path => "/usr/bin:/usr/sbin:/bin:/usr/local/bin:/usr/local/sbin:/sbin",
  creates => '/home/vagrant/.lein/repl-port',
  require => File['/usr/local/bin/grench'],
}

file { '/home/vagrant/.lein/repl-port':
  require => Exec["init-repl-port"]
}

# exec { 'start-nrepl':
#   command => 'nohup lein repl :headless :host "0.0.0.0" :port 4040 > /dev/null 2>&1 &',
#   path => "/usr/bin:/usr/sbin:/bin:/usr/local/bin:/usr/local/sbin:/sbin",
#   require => Exec['install-grench'],
# }

