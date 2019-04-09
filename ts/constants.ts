

#Legende der Bildpositionen:
#0 = Oben mittig
#1 = Oben rechts
#2 = Oben links
#8 = Unten mittig
#9 = Unten rechts
#10 = Unten links
#17 = Im Text rechts
#18 = Im Text links
#25 = Neben dem Text rechs
#26 = Neben dem Text links


# default settings for content elements in backend editor
TCAdefaults.tt_content {
	image_zoom = 1
    image_orient = 17
	imagewidth = 400
    imageborder.disabled = 1
	#imagewidth.disabled = 1
	#imageheight.disabled = 1
	#tx_perfectlightbox_activate = 1
	image_frames = 1
}

TCEFORM {
    tt_content {
        layout {
            altLabels {
                0 = Karten Design
                1 = Alternatives Design
            }
            removeItems = 2, 3, 4, 5
        }
    }
}

TCEFORM {
    pages {
        layout {
            altLabels {
                0 = Normales Design
                1 = Karten Design
            }
            removeItems = 2, 3
        }
    }
}
