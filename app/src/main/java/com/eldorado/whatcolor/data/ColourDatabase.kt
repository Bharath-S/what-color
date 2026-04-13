package com.eldorado.whatcolor.data

import kotlin.math.abs
import kotlin.math.min

data class NamedColour(
    val name: String,
    val description: String,
    val h: Float,   // 0–360
    val s: Float,   // 0–1
    val l: Float,   // 0–1
    val hex: String
)

object ColourDatabase {

    val colours: List<NamedColour> = listOf(
        // Reds
        NamedColour("Red", "A vivid primary red", 0f, 1f, 0.5f, "#FF0000"),
        NamedColour("Dark Red", "A deep, dark red", 0f, 1f, 0.27f, "#8B0000"),
        NamedColour("Crimson", "A rich, bold red", 348f, 0.83f, 0.47f, "#DC143C"),
        NamedColour("Firebrick", "A brick-like dark red", 0f, 0.68f, 0.42f, "#B22222"),
        NamedColour("Scarlet", "A vivid red with a hint of orange", 8f, 1f, 0.5f, "#FF2400"),
        NamedColour("Maroon", "A dark brownish-red", 0f, 1f, 0.25f, "#800000"),
        NamedColour("Burgundy", "A deep wine red", 345f, 0.63f, 0.32f, "#722F37"),
        NamedColour("Wine", "A rich, deep red like red wine", 352f, 0.56f, 0.35f, "#722F37"),
        NamedColour("Tomato", "A bright orange-red", 9f, 1f, 0.64f, "#FF6347"),
        NamedColour("Indian Red", "A warm muted red", 0f, 0.53f, 0.58f, "#CD5C5C"),
        NamedColour("Light Coral", "A soft, warm pink-red", 0f, 0.79f, 0.72f, "#F08080"),
        NamedColour("Salmon", "A warm pinkish-orange", 6f, 0.93f, 0.71f, "#FA8072"),
        NamedColour("Ruby", "A deep gem-like red", 337f, 0.79f, 0.41f, "#9B111E"),

        // Pinks
        NamedColour("Pink", "A light, soft pink", 350f, 1f, 0.88f, "#FFC0CB"),
        NamedColour("Hot Pink", "A vivid, bold pink", 330f, 1f, 0.71f, "#FF69B4"),
        NamedColour("Deep Pink", "A strong, saturated pink", 328f, 1f, 0.54f, "#FF1493"),
        NamedColour("Magenta", "A vibrant pink-purple", 300f, 1f, 0.5f, "#FF00FF"),
        NamedColour("Orchid", "A soft purple-pink", 302f, 0.59f, 0.65f, "#DA70D6"),
        NamedColour("Pale Pink", "A very light, delicate pink", 351f, 1f, 0.94f, "#FADADD"),
        NamedColour("Blush", "A gentle warm pink", 340f, 0.79f, 0.84f, "#F5A0A0"),
        NamedColour("Rose", "A soft romantic pink-red", 349f, 0.75f, 0.76f, "#FF91A4"),

        // Oranges
        NamedColour("Orange", "A bright, warm orange", 39f, 1f, 0.5f, "#FF8000"),
        NamedColour("Dark Orange", "A deep, rich orange", 33f, 1f, 0.5f, "#FF8C00"),
        NamedColour("Coral", "A warm pinkish-orange", 16f, 1f, 0.66f, "#FF7F50"),
        NamedColour("Burnt Orange", "A warm earthy orange", 21f, 0.82f, 0.43f, "#CC5500"),
        NamedColour("Tangerine", "A bright citrus orange", 31f, 1f, 0.53f, "#F28500"),
        NamedColour("Amber", "A warm golden orange", 45f, 1f, 0.5f, "#FFBF00"),
        NamedColour("Peach", "A soft, light orange", 28f, 1f, 0.86f, "#FFCBA4"),
        NamedColour("Apricot", "A warm pale orange", 35f, 1f, 0.83f, "#FBCEB1"),
        NamedColour("Rust", "A reddish-brown orange", 14f, 0.73f, 0.38f, "#B7410E"),
        NamedColour("Terracotta", "A warm earthy orange-red", 18f, 0.62f, 0.47f, "#C25B2A"),

        // Yellows
        NamedColour("Yellow", "A bright primary yellow", 60f, 1f, 0.5f, "#FFFF00"),
        NamedColour("Gold", "A warm, rich golden yellow", 51f, 1f, 0.5f, "#FFD700"),
        NamedColour("Light Yellow", "A very pale, soft yellow", 60f, 1f, 0.94f, "#FFFFE0"),
        NamedColour("Lemon", "A bright, zesty yellow", 59f, 1f, 0.59f, "#FFF44F"),
        NamedColour("Lemon Chiffon", "A very light creamy yellow", 54f, 1f, 0.9f, "#FFFACD"),
        NamedColour("Mustard", "A warm, earthy yellow", 45f, 0.79f, 0.52f, "#FFDB58"),
        NamedColour("Ochre", "A warm golden yellow-brown", 40f, 0.72f, 0.47f, "#CC7722"),
        NamedColour("Khaki", "A light yellowish-brown", 54f, 0.77f, 0.75f, "#F0E68C"),
        NamedColour("Dark Khaki", "A muted olive yellow", 56f, 0.38f, 0.58f, "#BDB76B"),
        NamedColour("Wheat", "A pale, straw-like yellow", 39f, 0.77f, 0.83f, "#F5DEB3"),
        NamedColour("Cream", "A very light warm white-yellow", 60f, 1f, 0.97f, "#FFFDD0"),
        NamedColour("Vanilla", "A soft, warm off-white yellow", 40f, 0.62f, 0.89f, "#F3E5AB"),

        // Greens
        NamedColour("Green", "A vivid primary green", 120f, 1f, 0.25f, "#008000"),
        NamedColour("Lime Green", "A bright, vivid lime green", 90f, 0.61f, 0.5f, "#32CD32"),
        NamedColour("Lime", "A very bright yellow-green", 75f, 1f, 0.5f, "#BFFF00"),
        NamedColour("Dark Green", "A deep, rich forest green", 120f, 1f, 0.2f, "#006400"),
        NamedColour("Forest Green", "A natural, deep green", 120f, 0.61f, 0.34f, "#228B22"),
        NamedColour("Hunter Green", "A dark, outdoorsy green", 120f, 0.72f, 0.22f, "#355E3B"),
        NamedColour("Olive", "A muted yellowish-green", 60f, 1f, 0.25f, "#808000"),
        NamedColour("Olive Drab", "A dull, earthy olive green", 80f, 0.6f, 0.35f, "#6B8E23"),
        NamedColour("Sea Green", "A cool, medium sea green", 146f, 0.5f, 0.36f, "#2E8B57"),
        NamedColour("Spring Green", "A vivid yellow-green", 150f, 1f, 0.5f, "#00FF80"),
        NamedColour("Mint Green", "A soft, fresh mint", 150f, 0.67f, 0.82f, "#98FF98"),
        NamedColour("Pale Green", "A very light, delicate green", 120f, 0.93f, 0.79f, "#98FB98"),
        NamedColour("Sage", "A soft, muted sage green", 80f, 0.27f, 0.62f, "#8F9779"),
        NamedColour("Jade", "A cool gemstone green", 150f, 0.44f, 0.41f, "#00A86B"),
        NamedColour("Emerald", "A rich, gem-like green", 140f, 0.5f, 0.36f, "#50C878"),
        NamedColour("Malachite", "A vivid deep green", 149f, 0.93f, 0.43f, "#0BDA51"),
        NamedColour("Teal Green", "A green with a blue hint", 162f, 0.72f, 0.37f, "#00827F"),
        NamedColour("Chartreuse", "A vivid yellow-green", 90f, 1f, 0.5f, "#7FFF00"),
        NamedColour("Grass Green", "The colour of fresh grass", 100f, 0.6f, 0.45f, "#5D8A45"),
        NamedColour("Seafoam Green", "A light aqua-tinged green", 146f, 0.44f, 0.76f, "#9FE2BF"),

        // Cyans & Teals
        NamedColour("Cyan", "A vivid blue-green", 180f, 1f, 0.5f, "#00FFFF"),
        NamedColour("Teal", "A deep blue-green", 180f, 1f, 0.25f, "#008080"),
        NamedColour("Dark Cyan", "A deep, rich cyan", 180f, 1f, 0.27f, "#008B8B"),
        NamedColour("Aquamarine", "A clear blue-green like water", 160f, 1f, 0.75f, "#7FFFD4"),
        NamedColour("Turquoise", "A bright blue-green", 174f, 0.72f, 0.56f, "#40E0D0"),
        NamedColour("Medium Turquoise", "A medium vibrant turquoise", 178f, 0.6f, 0.55f, "#48D1CC"),
        NamedColour("Light Cyan", "A very pale, cool cyan", 180f, 1f, 0.94f, "#E0FFFF"),
        NamedColour("Powder Blue", "A soft, cool blue", 187f, 0.52f, 0.8f, "#B0E0E6"),
        NamedColour("Cadet Blue", "A muted blue-grey", 182f, 0.25f, 0.5f, "#5F9EA0"),

        // Blues
        NamedColour("Blue", "A vivid primary blue", 240f, 1f, 0.5f, "#0000FF"),
        NamedColour("Dark Blue", "A deep, navy-like blue", 240f, 1f, 0.27f, "#00008B"),
        NamedColour("Navy Blue", "A classic dark navy blue", 240f, 1f, 0.25f, "#000080"),
        NamedColour("Prussian Blue", "A very dark, ink-like blue", 227f, 0.77f, 0.2f, "#003153"),
        NamedColour("Royal Blue", "A rich, confident blue", 225f, 0.73f, 0.57f, "#4169E1"),
        NamedColour("Cobalt Blue", "A vivid deep blue", 216f, 0.78f, 0.42f, "#0047AB"),
        NamedColour("Electric Blue", "A vivid neon blue", 210f, 1f, 0.56f, "#007FFF"),
        NamedColour("Cerulean", "A clear sky blue", 199f, 0.82f, 0.54f, "#2A9FDB"),
        NamedColour("Sky Blue", "A cool, light daytime blue", 197f, 0.71f, 0.73f, "#5DADE2"),
        NamedColour("Light Blue", "A soft, pale blue", 195f, 0.53f, 0.79f, "#ADD8E6"),
        NamedColour("Baby Blue", "A very light, gentle blue", 207f, 0.94f, 0.86f, "#AACFEE"),
        NamedColour("Cornflower Blue", "A soft medium blue", 219f, 0.79f, 0.66f, "#6495ED"),
        NamedColour("Dodger Blue", "A vivid, bright blue", 210f, 1f, 0.56f, "#1E90FF"),
        NamedColour("Steel Blue", "A metallic medium blue", 207f, 0.44f, 0.49f, "#4682B4"),
        NamedColour("Denim", "A classic jeans blue", 216f, 0.55f, 0.42f, "#1560BD"),
        NamedColour("Midnight Blue", "A very dark, almost black blue", 240f, 0.64f, 0.27f, "#191970"),
        NamedColour("Indigo", "A deep blue-purple", 275f, 1f, 0.25f, "#4B0082"),
        NamedColour("Periwinkle", "A soft blue-purple", 231f, 0.44f, 0.73f, "#CCCCFF"),

        // Purples & Violets
        NamedColour("Purple", "A rich primary purple", 300f, 1f, 0.25f, "#800080"),
        NamedColour("Dark Purple", "A deep, rich purple", 284f, 0.56f, 0.29f, "#6A0573"),
        NamedColour("Violet", "A bright blue-purple", 270f, 1f, 0.5f, "#8000FF"),
        NamedColour("Grape", "A dark, fruity purple", 285f, 0.5f, 0.35f, "#6F2DA8"),
        NamedColour("Boysenberry", "A deep red-purple", 315f, 0.52f, 0.3f, "#873260"),
        NamedColour("Lavender", "A light, soft purple", 240f, 0.67f, 0.94f, "#E6E6FA"),
        NamedColour("Wisteria", "A soft lilac purple", 288f, 0.38f, 0.7f, "#BFA3D6"),
        NamedColour("Plum", "A dark, fruity purple", 300f, 0.47f, 0.47f, "#DDA0DD"),
        NamedColour("Mauve", "A muted soft purple", 300f, 0.18f, 0.61f, "#E0B0FF"),
        NamedColour("Lilac", "A pale purple", 287f, 0.44f, 0.79f, "#C8A2C8"),
        NamedColour("Amethyst", "A medium gemstone purple", 288f, 0.44f, 0.49f, "#9966CC"),
        NamedColour("Thistle", "A pale purple-grey", 300f, 0.24f, 0.8f, "#D8BFD8"),
        NamedColour("Mulberry", "A deep red-purple", 320f, 0.63f, 0.38f, "#C54B8C"),
        NamedColour("Heather", "A soft blue-purple", 274f, 0.22f, 0.63f, "#9B7EC8"),

        // Browns
        NamedColour("Brown", "A warm, earthy brown", 25f, 0.76f, 0.31f, "#A52A2A"),
        NamedColour("Dark Brown", "A deep, rich brown", 16f, 0.55f, 0.23f, "#5C3317"),
        NamedColour("Light Brown", "A warm, medium brown", 30f, 0.59f, 0.47f, "#B5651D"),
        NamedColour("Chocolate", "A deep rich brown", 25f, 0.75f, 0.31f, "#7B3F00"),
        NamedColour("Tan", "A light warm brown", 34f, 0.44f, 0.69f, "#D2B48C"),
        NamedColour("Beige", "A very pale sandy brown", 60f, 0.56f, 0.91f, "#F5F5DC"),
        NamedColour("Ecru", "A warm, natural off-white brown", 50f, 0.48f, 0.87f, "#F5F0E0"),
        NamedColour("Sandy Brown", "A warm, sandy light brown", 28f, 0.87f, 0.67f, "#F4A460"),
        NamedColour("Sienna", "A warm earth red-brown", 19f, 0.56f, 0.4f, "#A0522D"),
        NamedColour("Burnt Sienna", "An earthy reddish-orange-brown", 18f, 0.68f, 0.44f, "#E97451"),
        NamedColour("Saddle Brown", "A rich leather brown", 25f, 0.76f, 0.31f, "#8B4513"),
        NamedColour("Caramel", "A warm sweet golden brown", 30f, 0.74f, 0.5f, "#C68642"),
        NamedColour("Coffee", "A rich dark brown", 20f, 0.59f, 0.31f, "#6F4E37"),
        NamedColour("Copper", "A reddish metallic brown", 23f, 0.57f, 0.5f, "#B87333"),
        NamedColour("Raw Umber", "A dark earthy brown", 25f, 0.52f, 0.27f, "#826644"),
        NamedColour("Mahogany", "A deep red-brown wood colour", 10f, 0.67f, 0.36f, "#C04000"),
        NamedColour("Taupe", "A warm brownish grey", 37f, 0.08f, 0.45f, "#483C32"),

        // Greys
        NamedColour("Grey", "A neutral mid-grey", 0f, 0f, 0.5f, "#808080"),
        NamedColour("Light Grey", "A soft, pale grey", 0f, 0f, 0.83f, "#D3D3D3"),
        NamedColour("Dark Grey", "A deep, strong grey", 0f, 0f, 0.33f, "#545454"),
        NamedColour("Silver", "A metallic light grey", 0f, 0f, 0.75f, "#C0C0C0"),
        NamedColour("Charcoal", "A very dark grey", 200f, 0.06f, 0.21f, "#36454F"),
        NamedColour("Ash Grey", "A warm, soft grey", 30f, 0.05f, 0.6f, "#B2BEB5"),
        NamedColour("Slate Grey", "A cool blue-grey", 210f, 0.13f, 0.5f, "#708090"),
        NamedColour("Gunmetal", "A dark, cool grey", 200f, 0.12f, 0.27f, "#2A3439"),
        NamedColour("Gainsboro", "A very light grey", 0f, 0f, 0.86f, "#DCDCDC"),
        NamedColour("Pewter", "A mid-tone blue-grey", 200f, 0.1f, 0.45f, "#6E7A7D"),

        // Blacks & Near-Blacks
        NamedColour("Black", "The absence of all colour", 0f, 0f, 0f, "#000000"),
        NamedColour("Near Black", "A very dark off-black", 0f, 0f, 0.07f, "#111111"),
        NamedColour("Jet Black", "An intense, pure black", 0f, 0f, 0.05f, "#0A0A0A"),
        NamedColour("Off Black", "A soft, warm near-black", 30f, 0.05f, 0.1f, "#1A1A1A"),

        // Whites & Near-Whites
        NamedColour("White", "Pure, bright white", 0f, 0f, 1f, "#FFFFFF"),
        NamedColour("Off White", "A slightly warm white", 40f, 0.5f, 0.97f, "#FAF9F6"),
        NamedColour("Ivory", "A warm, creamy white", 60f, 1f, 0.97f, "#FFFFF0"),
        NamedColour("Snow", "A pure, cool near-white", 0f, 1f, 0.99f, "#FFFAFA"),
        NamedColour("Ghost White", "A very pale cool white", 240f, 1f, 0.99f, "#F8F8FF"),
        NamedColour("Linen", "A warm off-white", 30f, 0.67f, 0.94f, "#FAF0E6"),
        NamedColour("Seashell", "A very light pinkish-white", 25f, 1f, 0.97f, "#FFF5EE"),
        NamedColour("Floral White", "A soft creamy white", 40f, 1f, 0.97f, "#FFFAF0"),
        NamedColour("Misty Rose", "A pale pinkish-white", 6f, 1f, 0.94f, "#FFE4E1"),
        NamedColour("Alice Blue", "A very pale icy blue-white", 208f, 1f, 0.97f, "#F0F8FF"),
        NamedColour("Honeydew", "A very pale mint green-white", 120f, 1f, 0.97f, "#F0FFF0"),
        NamedColour("Azure", "A very pale sky blue", 180f, 1f, 0.97f, "#F0FFFF"),
        NamedColour("Lavender Blush", "A pale pinkish-white", 340f, 1f, 0.97f, "#FFF0F5"),

        // Metallics
        NamedColour("Bronze", "A warm reddish-brown metal", 30f, 0.55f, 0.4f, "#CD7F32"),
        NamedColour("Brass", "A warm yellow-brown metal", 40f, 0.67f, 0.55f, "#B5A642"),
        NamedColour("Champagne", "A pale golden bubbly colour", 44f, 0.43f, 0.82f, "#F7E7CE"),
        NamedColour("Platinum", "A near-white metallic silver", 0f, 0f, 0.9f, "#E5E4E2"),
        NamedColour("Iron Grey", "A cool dark metallic grey", 210f, 0.05f, 0.35f, "#48494B"),
    )

    fun findNearest(h: Float, s: Float, l: Float, r: Int, g: Int, b: Int): ColourResult {
        var bestMatch = colours[0]
        var bestDistance = Float.MAX_VALUE

        for (colour in colours) {
            val distance = hslDistance(h, s, l, colour.h, colour.s, colour.l)
            if (distance < bestDistance) {
                bestDistance = distance
                bestMatch = colour
            }
        }

        // Max reasonable distance ~0.6, convert to 0-1 confidence
        val confidence = (1f - (bestDistance / 0.6f)).coerceIn(0f, 1f)

        return ColourResult(
            name = bestMatch.name,
            description = bestMatch.description,
            hex = bestMatch.hex,
            r = r, g = g, b = b,
            h = h, s = s, l = l,
            confidence = confidence
        )
    }

    private fun hslDistance(h1: Float, s1: Float, l1: Float, h2: Float, s2: Float, l2: Float): Float {
        val dh = min(abs(h1 - h2), 360f - abs(h1 - h2)) / 180f
        val ds = abs(s1 - s2)
        val dl = abs(l1 - l2)

        // For grey tones (low saturation), hue difference matters much less
        val hueWeight = if (s1 < 0.12f || s2 < 0.12f) 0.1f else 1.0f

        return hueWeight * dh * dh + 0.5f * ds * ds + 0.5f * dl * dl
    }
}
