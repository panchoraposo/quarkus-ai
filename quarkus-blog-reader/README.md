sudo subscription-manager register
sudo subscription-manager config --rhsm.manage_repos=1
sudo dnf -y update

sudo dnf install -y java-21-openjdk-devel
maven
git
podman
qemu-img
qemu-kvm
podman-gvproxy

sudo ln -s /usr/libexec/qemu-kvm /usr/bin/qemu-system-x86_64

echo 'export PATH=$PATH:/usr/libexec' >> ~/.bashrc

sudo modprobe kvm
sudo modprobe kvm-intel