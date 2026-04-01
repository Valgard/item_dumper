rootProject.name = "dev.valgard"

plugins {
    id("dev.scaffoldit") version "0.2.+"
}

hytale {
    usePatchline("release")
    useVersion("latest")

    manifest {
        Group = "Valgard"
        Name = "ItemDumper"
        Main = "dev.valgard.itemdumper.ItemDumper"
    }
}
