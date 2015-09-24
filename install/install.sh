# cat install.sh 
#!/bin/bash
install_dir=/usr/local/lib/gim
if [[ -e $install_dir ]];then
    rm -rf $install_dir/server-manager
else
    mkdir -p $install_dir
fi
sed -n '1,/^exit 0$/!p' $0 >$install_dir/server-manager.tar.gz

cd $install_dir
tar -xzvf server-manager.tar.gz

if [[ -e /etc/gim ]];then
    rm -rf /etc/gim/db.conf
    rm -rf /etc/gim/server-manager.conf
else
    mkdir /etc/gim
fi

cd $install_dir/server-manager
cp conf/* /etc/gim
cp init.d/gim-server-manager /etc/rc.d/init.d

chmod 755 bin/*
chmod 755 /etc/rc.d/init.d/gim-server-manager
chkconfig gim-server-manager on

exit 0
